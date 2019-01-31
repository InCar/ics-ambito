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
		 }
    };
		this.par = tool.extend(par, options, true);
		this.listeners = []; //自定义事件，用于监听插件的用户交互
    this.handlers = {};
    this.init(this.par);
	},
	init: function(obj) {

	}
};