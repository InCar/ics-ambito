import * as tool from "./tool.js";
import layui from "./config";
import $ from 'jquery';
export default {
	constructor: this,
	_initial: function(options) {
		var par = {
			tableName: '', //table表格名称
			headList: [ //表头
				{type: 'numbers', title: '序号'},
				{field: 'resourceName', align:'center', title: '资源名称'},
				{field:'type',
					align:'center',
					title: '资源类型',
					templet: (d) => {
						return d.type === 'menu' ? '菜单' : '按钮'
					}
				},
				{field: 'url', align:'center',title: '资源路径'},
				{field: 'icon', align:'center',title: '资源图标'},
				{field: 'isDisplay',
					align:'center',
					title: '显示',
					templet: (d) => {
						return d.isDisplay ? '显示' : '隐藏'
					}
				},
				{field: 'level', align:'center',title: '等级'},
				{fixed: 'right',
					align:'center',
					minWidth: 180,
					title: '操作',
					templet: function(d){
						let editBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">编辑</a>';
						let delBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
						return editBtn + delBtn;
					}
				}
			],
			id: '', // 表格唯一标识符
			apiUrl: '',   // 请求路径
			title: '', // 导出表头
			page: true, //开启分页
			cellMinWidth: 60, // 单元格的最小宽度
			limit: 10, // 每页显示的条数 值务必对应 limits 参数的选项
			// limits: [10,20,30,40,50,60,70,80,90],
			loading: true, // 是否显示加载条
			text: {
				none: '暂无相关数据' //默认：无数据
			},
			request: {
				pageName: 'pageNum' //页码的参数名称，默认：page
				,limitName: 'pageSize' //每页数据量的参数名，默认：limit
			},
			isLayer: false,
			layerConfig: {
			}
		};
		this.par = tool.extend(par, options, true);
		this.listeners = []; //自定义事件，用于监听插件的用户交互
		this.handlers = {};
	},
	init: function(fn){
		let obj = this.par;
		layui.use('table', () => {
			this.table = layui.table;
			let _this = this;
			//执行一个 table 实例
			this.tableIns = this.table.render({
				elem: obj.tableName,
				url: `${obj.apiUrl}/ics/resource/list`, //数据接口
				request: obj.request,
				parseData: function(res){ //res 即为原始返回的数据
					return {
						'code': res.code, //解析接口状态
						'msg': res.message, //解析提示文本
						'count': res.data.totalElements, //解析数据长度
						'data': res.data.content //解析数据列表
					};
				},
				id: obj.id,
				title: obj.title,
				text: obj.text,
				cellMinWidth: obj.cellMinWidth, // 单元格的最小宽度
				limit: obj.limit, // 每页显示的条数 值务必对应 limits 参数的选项
				limits: obj.limits,
				loading: obj.loading, // 是否显示加载条
				response: {
					statusCode: 200 //规定成功的状态码，默认：0
				}
				,page: obj.page //开启分页
				// ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
				// ,totalRow: true //开启合计行
				,cols: [
					obj.headList
				],
				done: function(res, curr, count){
					_this.tableData = res.data;
					//得到当前页码
					_this.curr = curr;
					//得到数据总量
					//	console.log(count);
				}
			});
			this.addTableListener();
			if (fn) fn( this.table, this.tableIns, _this.tableData);
		});
	},
	on: function(type, handler){
		// type: detail, del, edit
		if(typeof this.handlers[type] === 'undefined') {
			this.handlers[type] = [];
		}
		this.listeners.push(type);
		this.handlers[type].push(handler);
		return this;
	},
	emit: function(event){
		if(!event.target) {
			event.target = this;
		}
		if(this.handlers[event.type] instanceof Array) {
			let handlers = this.handlers[event.type];
			for(let i = 0, len = handlers.length; i < len; i++) {
				handlers[i](event);
				return true;
			}
		}
		return false;
	},
	addTableListener: function() {
		// 注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		this.table.on('tool(resourceTable)', (obj) => {
			// 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			switch(obj.event){
				case 'detail':
					this.checkDetail(obj.data);
					break;
				case 'del':
					this.checkDel(obj);
					break;
				case 'edit':
					this.checkAddAndEdit(obj.data);
					break;
			}
		});
	},
	checkDetail: function(data){
		let psr = this.par;
		let str = '';
		if (psr.isLayer) {
			for (let ps of psr.headList) {
				if (ps.field) {
					str += `<div class="layui-form-item">
								<label class="layui-form-label" style="width: 100px;">${ps.title}：</label>
								<div class="layui-input-block" style="line-height: 38px;">
									<span>${data[ps.field]}</span>
								</div>
							</div>`
				}
			}
			str = `<form class="layui-form" action="">${str}</form>`;
			layer.open({
				title: '查看',
				area: '500px',
				maxWidth: '500',
				content: str
			});
		}
		if(this.listeners.indexOf('detail') > -1) {
			this.emit({type:'detail',target: this, data: data})
		}
	},
	// 删除
	checkDel: function(obj){
		let data = obj.data;
		let _this = this;
		if(this.listeners.indexOf('del') > -1) {
			this.emit({type:'del',target: this, data: data});
		}
		layer.confirm('确定要删除吗？', function(index){
			// 向服务端发送删除指令
			tool.Ajax(`${_this.par.apiUrl}/ics/resource/${data.id}`, null, "delete")
				.then((data) => {
					if (data.result) {
						// transfer.onsuccess(data.data);
						obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
						layer.close(index);
					} else {
						layer.confirm(data.message, {icon: 3, title:'提示'});
					}
				}, (re) => {
					console.log(re);
				});
		});
	},
	/**
	 * 新增
	 * @param data
	 */
	checkAddAndEdit: function (data) {

		let str = '<form class="layui-form" style="padding: 10px 20px 1px 1px" action="" lay-filter="addForm">\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">资源类型</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="radio" name="type" lay-filter="type" value="menu" title="菜单" checked>\n' +
			'            <input type="radio" name="type" lay-filter="type" value="button" title="按钮">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">资源名称</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="resourceName" lay-verify="resourceName" placeholder="请输入资源名称" autocomplete="off" class="layui-input" maxlength="20">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item" id="sort">\n' +
			'        <label class="layui-form-label" style="width: 100px;">排序</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="sort" lay-verify="sort" placeholder="请输入排序" autocomplete="off" class="layui-input" maxlength="3">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">父级资源</label>\n' +
			'        <div class="layui-input-block">\n' +
			// '            <input type="text" name="parentId" lay-verify="parentId" placeholder="请输入父级资源" autocomplete="off" class="layui-input">\n' +
			'            <select name="parentId" lay-verify="" id="parentId">\n' +
			'            	<option value=""></option>\n' +
			'            	<option value="0">顶级资源</option>\n' +
			'            </select>\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item" id="url">\n' +
			'        <label class="layui-form-label" style="width: 100px;">路径</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="url" lay-verify="menuUrl" placeholder="请输入路径" autocomplete="off" class="layui-input" maxlength="100">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">图标</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="icon" lay-verify="icon" placeholder="请输入图标" autocomplete="off" class="layui-input" maxlength="100">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item" id="permission">\n' +
			'        <label class="layui-form-label" style="width: 100px;">权限标识符</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="permission" lay-verify="permission" placeholder="请输入权限标识符" autocomplete="off" class="layui-input" maxlength="100">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">显示/隐藏</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="checkbox" name="isDisplay" lay-skin="switch" lay-text="ON|OFF">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <div class="layui-input-block">\n' +
			'            <button class="layui-btn" lay-submit lay-filter="submitForm">立即提交</button>\n' +
			'            <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
			'        </div>\n' +
			'    </div>\n' +
			'</form>';

		let index = layer.open({
			type: 1,
			title: '新增',
			area: '500px',
			maxWidth: '500',
			content: str
		});
		//
		this.formSubmit(index, data);
	},
	/**
	 *  add edit submit
	 */
	formSubmit: function(index, data){
		let _this = this;
		layui.use(['form'], async function() {
			let form = layui.form;
			//
			$('#permission').hide();
			// 父级资源
			let allMenu = await _this.getAllMenu();
			$('#parentId option').remove();
			$("#parentId").append(`<option value=""></option>`);
			for (let menu of allMenu) {
				$("#parentId").append(`<option value="${menu.parentId + '-' + menu.parentIds}">${menu.resourceName}</option>`);
			}
			form.render();
			//表单初始赋值
			if (data) {
				if (data.type === 'button') {
					$('#sort').hide();
					$('#url').hide();
					$('#permission').show();
				} else if (data.type === 'menu') {
					$('#sort').show();
					$('#url').show();
					$('#permission').hide();
				}
				form.val('addForm', {
					'type': data.type,    // 'name': 'value'
					'resourceName': data.resourceName,
					'sort': data.sort,
					'parentId': data.parentId + '-' + data.parentIds,
					'url': data.url,
					'icon': data.icon,
					'permission': data.permission,
					'isDisplay': data.isDisplay
				});
			}
			let verifyFlag = true;  // true:menu  flag: button
			//自定义验证规则
			form.verify({
				resourceName: function(value){
					if(value.length < 1){
						return '角色名称不能为空';
					}
				},
				sort: (value) => {
					// 菜单
					if (verifyFlag) {
						if(value.length < 1){
							return '排序不能为空';
						}
						if (!(/^[0-9]*[1-9][0-9]*$/.test(value))) {
							return '排序格式不正确';
						}
					}
				},
				menuUrl: (value) => {
					// 菜单
					if (verifyFlag) {
						if(value.length < 1){
							return '路径不能为空';
						}
					}
				},
				permission: (value) => {
					// 按钮
					if (!verifyFlag) {
						if(value.length < 1){
							return '权限标识符不能为空';
						}
					}
				}
			});
			form.on('radio(type)', function (data) {
				// console.log(data.elem); // 得到radio原始DOM对象
				// console.log(data.value); // 被点击的radio的value值
				if (data.value === 'button') {
					$('#sort').hide();
					$('#url').hide();
					$('#permission').show();
					verifyFlag = false;
				} else if (data.value === 'menu') {
					$('#sort').show();
					$('#url').show();
					$('#permission').hide();
					verifyFlag = true;
				}
			});
			//监听提交
			form.on('submit(submitForm)', function (formData) {
				_this.submitForm(formData, index, data);
				return false;
			});
		});
	},
	/**
	 * add edit api
	 * @param formData 表单数据
	 * @param index 弹出框index
	 * @param data 要编辑的数据
	 */
	submitForm: async function (formData, index, data) {
		let _this = this;
		let params = tool.extend({}, formData.field, true);
		if (data) {  // 编辑
			params = tool.extend(data, params, true);
		} else {  // 新增
			//
		}
		//
		if (params.type === 'menu') {
			delete params.permission;
		} else {
			delete params.sort;
			delete params.url;
		}
		// parentId parentIds
		let arr = params.parentId.split('-');
		params.parentId = Number(arr[0]);
		params.parentIds = (arr[1] ? arr[1] : '0/') + arr[0];
		params.isDisplay = params.isDisplay === 'on';
		tool.Ajax(`${this.par.apiUrl}/ics/resource/save`, params, 'post')
			.then((data) => {
				if (data.result) {
					layer.close(index);
					_this.tableReload();
				} else {
					layer.confirm(data.message, {icon: 3, title:'提示'});
				}
			}, (re) => {
				console.log(re);
			});
	},
	// table reload
	tableReload: function(params) {
		let curr = 1;
		if (params) {
			this.params = params;
			curr = 1
		} else curr = this.curr;
		this.table.reload(this.tableIns.config.id, {
			where: this.params || {},  // 其他搜索条件参数，任意设
			page: {
				curr: curr //重新从第 1 页开始
			}
		});
	},
	/**
	 * 获取所有菜单资源
	 * @returns {Promise<any>}
	 */
	getAllMenu: function () {
		let _this = this;
		return new Promise(function (resolve, reject) {
			let params = {
				type: 'menu'
			};
			tool.Ajax(`${_this.par.apiUrl}/ics/resource/list`, params, 'get')
				.then((data) => {
					if (data.result) {
						resolve(data.data);
					} else {
						layer.confirm(data.message, {icon: 3, title:'提示'});
						resolve([]);
					}
				}, (re) => {
					console.log(re);
					resolve([]);
				});
		});
	}

};