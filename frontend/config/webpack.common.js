const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const dotenv = require('dotenv');
const webpack = require('webpack');

module.exports = (mode = {}) => {
  dotenv.config({
    path: `./env/.env.${mode || 'production'}`,
  });

  return {
    entry: `${path.resolve(__dirname, '../src')}/index.tsx`,
    module: {
      rules: [
        {
          test: /\.(ts|tsx|js|jsx)$/,
          use: 'babel-loader',
          exclude: /node_modules/,
        },
      ],
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
