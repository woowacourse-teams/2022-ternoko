const webpack = require('webpack');
const path = require('path');
const dotenv = require('dotenv');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = (mode = {}) => {
  dotenv.config({
    path: `./env/.env.${mode || 'production'}`,
  });

  return {
    entry: `${path.resolve(__dirname, '../src')}/index.tsx`,
    output: {
      filename: '[name].[contenthash].js',
      path: path.resolve(__dirname, '../dist'),
      publicPath: '/',
      clean: true,
    },
    plugins: [
      new webpack.EnvironmentPlugin(process.env),
      new HtmlWebpackPlugin({
        template: `${path.resolve(__dirname, '../public')}/index.html`,
      }),
      new webpack.ProvidePlugin({
        React: 'react',
      }),
    ],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, '/src'),
      },
      extensions: ['.js', '.ts', '.jsx', '.tsx', '.css', '.json'],
    },
  };
};
