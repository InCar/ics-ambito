const webpack = require('webpack');
const miniCssExtractPlugin = require("mini-css-extract-plugin");//用来抽离单独抽离css文件
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');//压缩css插件
module.exports = {
  // mode: "production",
  entry: ['@babel/polyfill', "./src/main/resources/static/js/index.js"],
  output: {
    path: __dirname + "/src/main/resources/static/dist",
    filename: "testbundle.js"
  },
  resolve: {
    // Add `.ts` and `.tsx` as a resolvable extension.
    extensions: [".ts", ".tsx", ".js"]
  },
  plugins: [
    new miniCssExtractPlugin({
      filename: "index.css"
    }),//抽离出来以后的css文件名称
    new OptimizeCssAssetsPlugin()//执行压缩抽离出来的css
  ],
  module: {
    rules: [
      // all files with a `.ts` or `.tsx` extension will be handled by `ts-loader`
      { test: /\.tsx?$/, loader: "ts-loader" },
      {
        test: /\.js$/,
         loader: "babel-loader"
      },
      {
        test:/\.css/,
        use:[miniCssExtractPlugin.loader,"css-loader",{
            loader: "postcss-loader",
            options: {
                plugins: () => [require('autoprefixer')],
                getLocalIdent: (context, localIdentName, localName, options) => {
                  return localName
                }
            }
        }]
      },
      {
        test:/\.scss$/,
        exclude: /node_modules/, 
        use:[miniCssExtractPlugin.loader,"css-loader",{
            loader: "postcss-loader",
            options: {
                plugins: () => [require('autoprefixer')]
            }
        },"sass-loader"]
      },
       {
        test: /\.(png|jpg|gif)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 5000
            }
          }
        ]
      },
      {
        test: /\.(otf|eot|svg|ttf|woff|woff2)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              name: '[name].[ext]',// 打包后的文件名称
              outputPath: '', // 默认是dist目录
              publicPath: '../font/', // 图片的url前面追加'../font'
              useRelativePath: true, // 使用相对路径
              limit: 50000 // 表示小于1K的图片会被转化成base64格式
            }
          }
        ]
      },
      {
        test: /\.(jpe?g|png|gif|svg)$/i,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[hash:3]_[name].[ext]',// 打包后的文件名称
              outputPath: '',
              publicPath: '../img/',
              useRelativePath: true
            }
          }
        ]
      },
    ]
  }
};