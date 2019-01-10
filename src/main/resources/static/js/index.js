import "./layui/css/layui.css";
import "./layui/layui.js";
// plugin.js
;(function(undefined) {
 "use strict"
 var _global;

 function extend(o, n, override) {
	for (var key in n) {
		if (n.hasOwnProperty(key) && (!o.hasOwnProperty(key) || override)) {
			o[key] = n[key];
		}
	}
	return o;
};

// 插件构造函数 - 返回数组结构
function carTable(options) {
	this._initial(options);
};
carTable.prototype = {
	constructor: this,
	_initial: function(options) {
		var par = {
			tableName: '', //table表格名称
		};
		this.par = extend(par, options, true);
		//判断是否存在class属性方法
		this.hasClass = function(elements, cName) {
			return !!elements.className.match(new RegExp("(\\s|^)" + cName + "(\\s|$)"));
		}
		//添加class属性方法
		this.addClass = function(elements, cName) {
			if (!this.hasClass(elements, cName)) {
				elements.className += " " + cName;
			};
		};
		//删除class属性方法 elements当前结构  cName类名
		this.removeClass = function(elements, cName) {
			if (this.hasClass(elements, cName)) {
				elements.className = elements.className.replace(new RegExp("(\\s|^)" + cName + "(\\s|$)"), " "); // replace方法是替换
			};
		};
		this.init(this.par);
	},
	init: function(obj){
		layui.use('table', function(){
			var table = layui.table;
			
			  //执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 420
		,url: 'ics/user/list' //数据接口
		,request: {
			pageName: 'pageNum' //页码的参数名称，默认：page
			,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
    ,title: '用户表'
    ,page: true //开启分页
    ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
    ,totalRow: true //开启合计行
    ,cols: [[ //表头
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
      ,{field: 'username', title: '用户名', width:80}
      ,{field: 'experience', title: '积分', width: 90, sort: true, totalRow: true}
      ,{field: 'sex', title: '性别', width:80, sort: true}
      ,{field: 'score', title: '评分', width: 80, sort: true, totalRow: true}
      ,{field: 'city', title: '城市', width:150} 
      ,{field: 'sign', title: '签名', width: 200}
      ,{field: 'classify', title: '职业', width: 100}
      ,{field: 'wealth', title: '财富', width: 135, sort: true, totalRow: true}
      ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
    ]]
  });
			
		});
	}
};

 // 将插件对象暴露给全局对象
 _global = (function(){ return this || (0, eval)('this'); }());
 if (typeof module !== "undefined" && module.exports) {
     module.exports = carTable;
 } else if (typeof define === "function" && define.amd) {
     define(function(){return carTable;});
 } else {
     !('carTable' in _global) && (_global.carTable = carTable);
 }
}());
