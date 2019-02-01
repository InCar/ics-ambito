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
					 {type:'numbers', title: '序号'}
					,{field:'orgName', edit:'text',width:300, title: '组织名称'}
					,{width:200,title: '操作', align:'center'/*toolbar: '#barDemo'*/
							,templet: function(d){
							var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
							var editBtn='<a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>';
							var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
							return addBtn+editBtn+delBtn;
					}
					}
			]
				,page:false
		 },
		 clickRow: (data, index) => {}, // 点击行获取行数据
		 setData: (data, index) => {} // 整理数据
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
			,parseData: (res) => {//数据加载后回调
				 return this.par.setData(res);
			}
			,onClickRow: (index, data) => {
				 this.par.clickRow(data, index)
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
							if (data.code === "200") obj.del();
							else layer.confirm(data.message, {icon: 3, title:'提示'});
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
			email: "456@qq.com",
			orgName:"财务部",
			orgCode: "cwb",
			fullName: "英卡财务部"
		}
		// 组装必须的parentCode，parentCodes
		let code = {parentCode: obj.orgCode, parentCodes: `${obj.parentCodes}${obj.orgCode},`}
		let params = Object.assign(code, this.par.addParams);
		this.query(this.par.config.id, params);
  },
	// 重载
	reload: function () {
		this.treeGrid.reload(tableId,{
				page:{
						curr:1
				}
		});
	},
	/**
	 * 刷新数据
	 * @param id 唯一标识符
	 * @param params 参数
	 */
	query: function (id, params) {
		tool.Ajax(`${this.par.apiUrl}/ics/sysorg/save`, params, "post")
		.then((data) =>{
					this.treeGrid.query(id, {
						where:{
						}
					});
		})
 }
};