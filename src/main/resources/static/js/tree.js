import * as tool from "./tool.js";
import layui from "./config"
export default {
	constructor: this,
	_initial: function(options) {
		var par = {
      treeName: '',
      treeApi: ""
      ,nodes: [{ //节点
        name: '父节点1'
        ,children: [{
          name: '子节点11'
        },{
          name: '子节点12'
        }]
      },{
        name: '父节点2（可以点左侧箭头，也可以双击标题）'
        ,children: [{
          name: '子节点21'
          ,children: [{
            name: '子节点211'
          }]
        }]
      }]
    };
		this.par = tool.extend(par, options, true);

		this.listeners = []; //自定义事件，用于监听插件的用户交互
    this.handlers = {};
    this.init(this.par);
	},
	init: function(obj){
    tool.Ajax.get(`${obj.treeApi}/ics/sysorg/orgTree`, {}, (data) => {
      console.log(data);
    })
		layui.use('tree', () => {
      layui.tree(obj);
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
	addTreeListener: function() {

	},
};