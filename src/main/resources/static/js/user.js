import * as tool from "./tool.js";
import layui from "./config";
export default {
	constructor: this,
	_initial: function(options) {
		var par = {
			tableName: '', //table表格名称
			headList: [ //表头
				{type: 'numbers', title: '序号'}
				,{field: 'username', align:'center',title: '账户名'}
				,{field: 'realName', align:'center',title: '姓名'}
				,{field: 'email', align:'center',title: '邮箱'}
				,{field: 'createTimeLong', align:'center',minWidth: 160, title: '创建时间'
					,templet: (d) => {
						return tool.DateFormat(new Date(d.createTimeLong), "yyyy-MM-dd hh:mm:ss")
					}}
				,{fixed: 'right', align:'center', toolbar: '#barDemo'}
			],
			id: "", // 表格唯一标识符
			url: '',   // 请求路径
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
				limits: obj.limits,
				url: `${obj.url}/ics/user/list`, //数据接口
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
					_this.tableData = res.data;
					//得到当前页码
					//	console.log(curr);
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
			var handlers = this.handlers[event.type];
			for(var i = 0, len = handlers.length; i < len; i++) {
				handlers[i](event);
				return true;
			}
		}
		return false;
	},
	addTableListener: function() {
		//自定义事件
		// layui.onevent("toolManage", "add", (fn) => {
		// 	fn();
		// });
		// 	layui.event("toolManage", "add", () => {
		// 		console.log(123);
		// 	});
		this.table.on('tool(test)', (obj) => { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			//获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			switch(obj.event){
				case 'detail':
					this.checkDetail(obj.data);
					break;
				case 'del':
					this.checkDel(obj.data);
					break;
				case 'edit':
					this.checkEdit(obj.data);
					break;
			};
		});
	},
	checkDetail: function(data){
		let psr = this.par;
		if (psr.isLayer) {
			layer.open({
				title: '查看'
				,content: '展示信息'
			});
		}
		if(this.listeners.indexOf('detail') > -1) {
			this.emit({type:'detail',target: this, data: data})
		};
	},
	checkDel: function(data){
		if(this.listeners.indexOf('del') > -1) {
			this.emit({type:'del',target: this, data: data});
		};
		layer.confirm('真的删除行么', function(index){
			obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
			layer.close(index);
			//向服务端发送删除指令
		});
	},
	checkEdit: function(data){
		if(this.listeners.indexOf('edit') > -1) {
			this.emit({type:'edit',target: this, data: data});
		};
		//同步更新缓存对应的值
		obj.update({
			username: '123'
			,title: 'xxx'
		});
	}
};