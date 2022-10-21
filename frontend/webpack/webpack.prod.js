const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const { ESBuildMinifyPlugin } = require('esbuild-loader');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');

module.exports = () =>
  merge(common('production'), {
    mode: 'production',
    module: {
      rules: [
        {
          test: /\.(ts|tsx|js|jsx)$/,
          loader: 'esbuild-loader',
          options: {
            loader: 'tsx',
            target: 'esnext',
          },
        },
        {
          test: /\.(sa|sc|c)ss$/i,
          use: [MiniCssExtractPlugin.loader, 'css-loader'],
        },
      ],
    },
    plugins: [new MiniCssExtractPlugin()],
    optimization: {
      usedExports: true,
      minimizer: [
        new ESBuildMinifyPlugin({
          target: 'esnext',
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
