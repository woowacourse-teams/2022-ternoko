const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

module.exports = () =>
  merge(common('development'), {
    mode: 'development',
    devtool: 'eval',
    devServer: {
      https: true,
      open: true,
      hot: true,
      compress: true,
      port: 3000,
      historyApiFallback: true,
      liveReload: true,
    },
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
          use: ['style-loader', 'css-loader'],
        },
      ],
    },
    plugins: [new BundleAnalyzerPlugin()],
  });
