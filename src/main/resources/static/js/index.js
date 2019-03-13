
import user from "./user"
import tree from "./tree"
import login from "./login"
import vehicle from "./vehicle"
import role from "./role"
import resource from "./resource"

// plugin.js
;(function(undefined) {
 "use strict"
 var _global;

// 插件构造函数 - 返回数组结构

var PermissionInfo = {
	User: function(options) {
		this._initial(options);
	},
	Tree: function(options) {
		this._initial(options);
	},
	Login: function(options,cb) {
		this._initial(options, cb);
	},
	Vehicle: function(options) {
		this._initial(options);
	},
	Role: function(options) {
		this._initial(options);
	},
	Resource: function (options) {
		this._initial(options);
	}
};
PermissionInfo.User.prototype = user;
PermissionInfo.Tree.prototype = tree;
PermissionInfo.Login.prototype = login;
PermissionInfo.Vehicle.prototype = vehicle;
PermissionInfo.Role.prototype = role;
PermissionInfo.Resource.prototype = resource;

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
