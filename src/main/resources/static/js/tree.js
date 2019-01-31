import * as tool from "./tool.js";
import layui from "./config"
export default {
	constructor: this,
	_initial: function(options) {
		var par = {
			elem: '',
      apiUrl: "",
			params: {id: 1}, // 传入id
			addParams: { // 新增参数
			},
      config: {
				 id: "",
				 idField:'orgCode' //必須字段 
				,method:'get'
				,cellMinWidth: 100
				,treeId:'orgCode'//树形id字段名称
				,treeUpId:'parentCode'//树形父id字段名称
				,treeShowName:'orgName'//以树形式显示的字段
				,cols: [
					{width:100,title: '操作', align:'center'/*toolbar: '#barDemo'*/
							,templet: function(d){
							var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
							var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
							return addBtn+delBtn;
					}
					}
					,{field:'orgName', edit:'text',width:300, title: '组织名称'}
			]
				,page:false
				,parseData:function (res) {//数据加载后回调
				}
				,onClickRow:function (index, o) {
					// console.log(o)
			}
		}
    };
		this.par = tool.extend(par, options, true);

		this.listeners = []; //自定义事件，用于监听插件的用户交互
    this.handlers = {};
    this.init(this.par);
	},
	init: function(obj) {
		layui.config({
			base: '/dist/extend/'
		}).extend({
			treeGrid:'treeGrid'
		}).use('treeGrid', () => {
		this.treeGrid = layui.treeGrid;
    this.treeGrid.render({
			id: obj.config.id
			,elem: obj.elem
			,idField:obj.config.idField
			,url:`${obj.apiUrl}/ics/sysorg/list`
			,method:obj.config.method
			,cellMinWidth: obj.config.cellMinWidth
			,treeId:obj.config.treeId//树形id字段名称
			,treeUpId:obj.config.treeUpId//树形父id字段名称
			,treeShowName:obj.config.treeShowName//以树形式显示的字段
			,cols: [obj.config.cols]
			,page:obj.config.page
			,parseData:function (res) {//数据加载后回调
			}
			,onClickRow:function (index, o) {
				// console.log(o)
		}
	});
		// 工具栏参数与lay-filter匹配
		this.treeGrid.on('tool('+obj.config.id+')', (obj) => {
				if(obj.event === 'del'){//删除行
						this.del(obj);
				}else if(obj.event==="add"){//添加行
						this.add(obj.data);
				}
		});
		});
	},
	del: function (obj) {
		layer.confirm("你确定删除数据吗？", {icon: 3, title:'提示'},
				(index) => {//确定回调
						tool.Ajax(`${this.par.apiUrl}/ics/sysorg/delete/${obj.data.id}`, {}, "delete")
						.then((data) => {
							obj.del();
							console.log(data);
						}, (re) => {
							console.log(re);
						})
						layer.close(index);
				},function (index) {//取消回调
					 layer.close(index);
				}
		);
  },
	add: function (obj) {
		this.par.addParams = {
			email: "123@qq.com",
			orgName:"人事部",
			orgCode: "rsb",
			fullName: "英卡人事部"
		}
		// 组装必须的parentCode，parentCodes
		let code = {parentCode: obj.orgCode, parentCodes: `${obj.parentCodes}${obj.orgCode},`}
		let params = Object.assign(code, this.par.addParams);
		tool.Ajax(`${this.par.apiUrl}/ics/sysorg/save`, params, "post")
		.then((data) =>{
			if(data.code === "200") {
				this.query(this.par.config.id, obj);
			}
		})
  },
	// 重载
	reload: function () {
		this.treeGrid.reload(tableId,{
				page:{
						curr:1
				}
		});
	},
	// 刷新数据
	query: function (id, obj) {
		tool.Ajax(`${this.par.apiUrl}/ics/sysorg/list`, {}, "get")
		.then(() => {
			this.treeGrid.query(id, {
				where:{
						name:obj.orgName
				}
		  });
		})
 },
	recuNodes: function(child) {
		child.map(item => {
				item.name = item.orgName;
				item.spread = false; // 默认不展开
				if(item.children.length) this.recuNodes(item.children)
		})
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
	addTreeListener: function() {

	},
};