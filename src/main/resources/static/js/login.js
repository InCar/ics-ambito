import * as tool from "./tool.js";
import layui from "./config";
import md5 from 'md5';
export default {
    constructor: this,
    _initial: function(options, cb) {
        var par = {
            elem: '',
            apiUrl: "",
            params: {id: 1}, // 传入id
            addParams: { // 新增参数
            },
            config: {
                nameLabel: "用户名",
                namePlaceholder: "请输入用户名",
                pswLabel: "密码",
                pswPlaceholder: "请输入密码",
            }
        };
        this.par = tool.extend(par, options, true);
        this.listeners = []; //自定义事件，用于监听插件的用户交互
        this.handlers = {};
        this.dom = document.getElementById(this.par.elem);
        this.init(this.par, cb);
    },
    init: function(obj, cb) {
        let str = `<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item">
      <label class="layui-form-label">${obj.config.nameLabel}</label>
      <div class="layui-input-block">
        <input type="text" name="username" lay-verify="title" autocomplete="off" placeholder=${obj.config.namePlaceholder} class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">${obj.config.pswLabel}</label>
      <div class="layui-input-block">
        <input type="password" name="password" placeholder=${obj.config.pswPlaceholder} autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <div class="layui-input-block">
        <button class="layui-btn" lay-submit="" lay-filter="formDemo">登录</button>
      </div>
    </div>
  </form>`
        this.dom.innerHTML = str;
        this.formSubmit(cb);
    },
    formSubmit: function(cb){
        let _this = this;
        layui.use(['form'], function() {
            var form = layui.form

            //自定义验证规则
            form.verify({
                title: function(value){
                    if(value.length < 5){
                        return '标题至少得5个字符啊';
                    }
                }
                ,pass: [
                    /^[\S]{6,12}$/
                    ,'密码必须6到12位，且不能出现空格'
                ]
                ,content: function(value){
                    layedit.sync(editIndex);
                }
            });
            //监听提交
            form.on('submit(formDemo)', function(data){
                _this.loginSubmit(data,cb);
                return false;
            });
        });
    },
    /**
     * 登录 api
     */
    loginSubmit: function (data,cb) {
        let params = tool.extend({}, data.field, true);
        params.password = md5(params.password);
        tool.Ajax(`${this.par.apiUrl}/ics/user/login`, params, "post")
            .then((data) => {
                if (data.result) {
                    // transfer.onsuccess(data.data);
                    cb(data);
                } else {
                    cb(data);
                    layer.confirm(data.message, {icon: 3, title:'提示'});
                }
            }, (re) => {
                console.log(re);
                cb(data);
            });
    },
};