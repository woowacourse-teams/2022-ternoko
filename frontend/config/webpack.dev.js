const { merge } = require('webpack-merge');
const common = require('./webpack.common');

module.exports = () =>
  merge(common('development'), {
    mode: 'development',
    devtool: 'inline-source-map',
    devServer: {
      https: true,
      open: false,
      hot: true,
      compress: true,
      port: 3000,
      historyApiFallback: true,
      liveReload: true,
    },
    output: {
      filename: '[name].[contenthash].js',
      publicPath: '/',
    },
    module: {
      rules: [
        {
          test: /\.(sa|sc|c)ss$/i,
          use: ['style-loader', 'css-loader'],
        },
      ],
    },
  });
