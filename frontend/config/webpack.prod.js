const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');

module.exports = () =>
  merge(common('production'), {
    mode: 'production',
    devtool: 'cheap-module-source-map',
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
      path: path.resolve(__dirname, '../dist'),
      publicPath: '/',
      clean: true,
    },
    module: {
      rules: [
        {
          test: /\.(sa|sc|c)ss$/i,
          use: [MiniCssExtractPlugin.loader, 'css-loader'],
        },
      ],
    },
    plugins: [new MiniCssExtractPlugin()],
    optimization: {
      usedExports: true,
      minimize: true,
      minimizer: [
        new TerserPlugin({
          terserOptions: {
            compress: {
              drop_console: false,
            },
          },
        }),
        new CssMinimizerPlugin(),
      ],
      splitChunks: {
        chunks: 'all',
      },
    },
    performance: {
      hints: false,
      maxEntrypointSize: 512000,
      maxAssetSize: 512000,
    },
  });
