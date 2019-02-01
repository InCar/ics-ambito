
import table from "./user"
import tree from "./tree"

// plugin.js
;(function(undefined) {
 "use strict"
 var _global;

// 插件构造函数 - 返回数组结构

var PermissionInfo = {
	Table: function(options) {
		this._initial(options);
	},
	Tree: function(options) {
		this._initial(options);
	}
}
PermissionInfo.Table.prototype = table;
PermissionInfo.Tree.prototype = tree;

 // 将插件对象暴露给全局对象
 _global = (function(){ return this || (0, eval)('this'); }());
 if (typeof module !== "undefined" && module.exports) {
     module.exports = PermissionInfo;
 } else if (typeof define === "function" && define.amd) {
     define(function(){return PermissionInfo;});
 } else {
     !('PermissionInfo' in _global) && (_global.PermissionInfo = PermissionInfo);
 }
}());
