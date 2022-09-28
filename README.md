# Deepdive Dapp项目

## 目录结构：

Contarct: Dapp的Conflux合约部分

java：Dapp的Java-sdk-Conflux，Dapp的后台项目；

vue: Dapp的前端项目；

## Contract

参考 Conflux 合约部署方法

## java

>使用Spring-boot 启动

### 配置文件介绍：

```
qiniu:
  AccessKey: X_SRypTH117tcdS69CFePT7FFedcigA1K4_hZGlG # 七牛云Ak
  SecretKey: LxOyfhej3bXw4a8JGxhdtxVkPDomXZoGdzX4LbWA # 七牛云SK
  zone: BEI_MEI # 七牛云静态服务器的位置
  resourcesUrl: cdn.ddinfo.top # 静态服务器 域名
  bucket: deepdivemi # 静态服务器 bucket名称
server:
  port: 8888 # 后台服务启动的端口
```

## vue

启动:

> npm install
>
> npm run dev

操作方式

> 登录界面设计了两种方式，目前是通过私钥的方式导入钱包地址来实现登录
> 示例私钥：0x2434542ad07a0a9c00a6acd6b53a17ca495251531bab5e3cc6ff2361c285d151
> 登录进来之后首页是暂时没有开发的，整个dapp有两个模块的功能，一个是铸造厂，一个是交易所；铸造厂包括查看已经发行的NFT（这些NFT都对应有关碳排放的相关项目）我们点进tokenId为60的NFT，然后这些数据都是在GFS平台，在详细信息中我们可以看到该NFT和Credit的发行时间，以及他的交易和释放的记录。然后再左边还可以看到该NFT的metaData信息
> 在铸造功能中，我们可以通过指定给NFTmetaData信息的数据链接，也可以通过Credit ID来生产metaData，输入creditId后要经过验证（验证该CreditID是否存在），验证完成后就可以创建新的NFT了。
> 在交易所中的交易记录功能可以查询发生在整个合约上的所有交易记录，也可以指定一些要素来筛选交易记录。比如使用tokenId。
> 发起交易的功能中可以将NFT转移给你想要转移的人，只要你知道他的Conflux地址，另外你要有足够的余额。
> 释放功能发生在碳指标被使用的时候，例如我们使用了credit Id为104098的碳项目中的碳指标100吨，我们知道其对应的tokenId为61，选择tokenID为61，输入释放的数量，提交交易。碳指标使用就完成了。

介绍视频：

http://cdn.ddinfo.top/back-manage%20-%20%E8%81%94%E6%83%B3%E6%B5%8F%E8%A7%88%E5%99%A8%202022-09-22%2020-57-06.mp4

# demo

