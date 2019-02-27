import 'layui-src';
import 'layui-src/dist/css/layui.css';
console.log(window.location.host);
layui.config({
  dir: 'http://' + window.location.host + '/dist/'
})

export default layui