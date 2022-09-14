/**
 * 开发环境
 */
;(function () {
  window.SITE_CONFIG = {}

  // api接口请求地址
  window.SITE_CONFIG['baseUrl'] = 'http://127.0.0.1:8888'
  window.SITE_CONFIG['userInfo'] = {}

  // api接口请求地址
  window.SITE_CONFIG['payUrl'] = 'http://127.0.0.1:8888'

  // 静态资源文件url
  window.SITE_CONFIG['resourcesUrl'] = 'http://zjnuasset.upphu.vip/'
  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain'] = './' // 域名
  window.SITE_CONFIG['version'] = ''   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl'] = window.SITE_CONFIG.domain + window.SITE_CONFIG.version
})()
