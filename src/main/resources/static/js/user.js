import * as tool from "./tool.js";
import layui from "./config";
import md5 from "md5";
export default {
	constructor: this,
	_initial: function(options) {
		var par = {
			tableName: '', //table表格名称
			headList: [ //表头
				{type: 'numbers', title: '序号'},
				{field: 'username', align:'center',title: '账号'},
				{field: 'realName', align:'center',title: '真实姓名'},
                {field: 'gender', align:'center',title: '性别',
                    templet: (d) => {
                        return Number(d.gender) === 0 ? '男' : '女'
                    }
                },
                {field: 'phone', align:'center',title: '手机号'},
				{field: 'email', align:'center',title: '邮箱'},
                {field: 'state', align:'center',title: '状态',
                    templet: (d) => {
                        return d.state === 'enabled' ? '正常' : '禁用'
                    }
                },
				{field: 'createTime', align:'center',minWidth: 160, title: '创建时间',
                    templet: (d) => {
						return tool.DateFormat(new Date(d.createTime), "yyyy-MM-dd hh:mm:ss")
					}
                },
                {fixed: 'right', align:'center',minWidth: 180, title: '操作',
                    templet: function(d){
                        let disableBtn = '';
                        if (d.state === 'enabled') {
                            disableBtn = '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="disable">禁用</a>';
                        } else {
                            disableBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="enable">启用</a>';
                        }

                        let delBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                        return disableBtn+delBtn;
                    }
                }
			],
			id: "", // 表格唯一标识符
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
				url: `${obj.apiUrl}/ics/user/list`, //数据接口
				request: obj.request,
				parseData: function(res){ //res 即为原始返回的数据
					return {
						"code": res.code, //解析接口状态
						"msg": res.message, //解析提示文本
						"count": res.data.totalElements, //解析数据长度
						"data": res.data.content //解析数据列表
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
				    // console.log(res, curr, count);
					_this.tableData = res.data;
					//得到当前页码
					//	console.log(curr);
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
		this.table.on('tool(test)', (obj) => {
			// 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			switch(obj.event){
				case 'detail':
					this.checkDetail(obj.data);
					break;
				case 'del':
					this.checkDel(obj);
					break;
				case 'edit':
					this.checkEdit(obj.data);
					break;
				case 'disable':
					this.checkEnable(obj, 'disabled');
					break;
				case 'enable':
					this.checkEnable(obj, 'enabled');
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
            tool.Ajax(`${_this.par.apiUrl}/ics/user/delete/${data.id}`, null, "delete")
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
	// 编辑
	checkEdit: function(obj){
	    let data = obj.data;
		if(this.listeners.indexOf('edit') > -1) {
			this.emit({type:'edit',target: this, data: data});
		}
		//同步更新缓存对应的值
		obj.update({
			username: '123'
			,title: 'xxx'
		});
		// todo 更新服务器信息
	},
    // 新增
	checkAdd: function(){
		// let psr = this.par;
		let str = '<form class="layui-form" style="padding: 10px 20px 1px 1px" action="">\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">账号</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="username" lay-verify="username" placeholder="请输入用户名" autocomplete="off" class="layui-input">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">密码</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="password" name="password" lay-verify="password" placeholder="请输入密码" autocomplete="off" class="layui-input">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">真实姓名</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="realName" lay-verify="realName" placeholder="请输入真实姓名" autocomplete="off" class="layui-input">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">性别</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="radio" name="gender" value="0" title="男">\n' +
			'            <input type="radio" name="gender" value="1" title="女" checked>\n' +
			'        </div>\n' +
			'    </div>\n' +
            '    <div class="layui-form-item">\n' +
            '        <label class="layui-form-label" style="width: 100px;">手机号</label>\n' +
            '        <div class="layui-input-block">\n' +
            '            <input type="text" name="phone" lay-verify="phoneStr" placeholder="请输入手机号" autocomplete="off" class="layui-input">\n' +
            '        </div>\n' +
            '    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <label class="layui-form-label" style="width: 100px;">邮箱</label>\n' +
			'        <div class="layui-input-block">\n' +
			'            <input type="text" name="email" lay-verify="emailStr" placeholder="请输入邮箱" autocomplete="off" class="layui-input">\n' +
			'        </div>\n' +
			'    </div>\n' +
			'    <div class="layui-form-item">\n' +
			'        <div class="layui-input-block">\n' +
			'            <button class="layui-btn" lay-submit lay-filter="formAdd">立即提交</button>\n' +
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
		this.formAddSubmit(index);
	},
    // add submit
	formAddSubmit: function(index){
		let _this = this;
		layui.use(['form'], function() {
			let form = layui.form;
			form.render();
			//自定义验证规则
			form.verify({
				username: function(value){
					if(value.length < 5){
						return '用户名至少得5个字符';
					}
				},
				password: [
					/^[\S]{6,12}$/,
					'密码必须6到12位，且不能出现空格'
				],
                realName: function(value){
					if(value.length > 10){
						return '用户名不能大于10个字符';
					}
				},
                emailStr: function(value){
                    if(value.length > 0){
                        let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                        if (!(reg.test(value))) {
                            return '邮箱格式不正确';
                        }
                    }
                },
                // phoneStr: function(value){
                //     if(value.length > 0){
                //         let reg = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
                //         if (!(reg.test(value))) {
                //             return '手机号格式不正确';
                //         }
                //     }
                // },
				// emailStr: [
				// 	/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/,
				// 	'邮箱格式不正确'
				// ],
				phoneStr: [
					/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/,
					'手机号格式不正确'
				],
			});
			//监听提交
			form.on('submit(formAdd)', function(data){
				_this.addSubmit(data, index);
				return false;
			});
		});
	},
	/**
	 * 新增 api
	 */
	addSubmit: function (data, index) {
	    let _this = this;
		let params = tool.extend({}, data.field, true);
		params.password = md5(params.password);
		tool.Ajax(`${this.par.apiUrl}/ics/user/rigister`, params, "post")
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
	// 启用/禁用
	checkEnable: function (obj, name) {
		let _this = this;
		let data = obj.data;
		if(this.listeners.indexOf(name) > -1) {
			this.emit({type: name,target: this, data: data});
		}
		let str = '';
		if (name === 'disabled') str = '禁用';
		else str = '启用';
		layer.confirm(`确定要${str}吗？`, function(index){
			layer.close(index);
			// 向服务端发送启用指令
            let params = tool.extend({}, data, true);
            params.state = name;
            tool.Ajax(`${_this.par.apiUrl}/ics/user/update`, params, "post")
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
    }
};