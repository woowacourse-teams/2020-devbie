const path = require("path");

module.exports = {
  outputDir: path.resolve(__dirname, "../../" + "docker/nginx/static"),
  devServer: {
    host: "localhost",
    proxy: {
      "/api": {
        target: "http://localhost:9000",
        ws: true,
        changeOrigin: true
      },
      "/chat": {
        target: "http://localhost:9000",
        ws: true,
        changeOrigin: true
      }
    }
  }
};
