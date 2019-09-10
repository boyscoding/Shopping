# Shopping
该项目是仿京东购物商城项目，SSM+dubbo分布式开源项目,供大家学习，博客还在持续完成,项目源自于网络教学。

# 架构

- 使用了面向服务的架构（Service-Oriented Architecture， SOA）：Service Oriented Architecture面向服务的架构。也就是把工程都拆分成服务层工程、表现层工程。服务层中包含业务逻辑，只需要对外提供服务即可。表现层只需要处理和页面的交互，业务逻辑都是调用服务层的服务来实现。工程都可以独立部署。

- 功能模块： 
  - 后台管理系统：管理商品、订单、类目、商品规格属性、用户管理以及内容发布等功能。
  - 前台系统：用户可以在前台系统中进行注册、登录、浏览商品、首页、下单等操作。
  - 会员系统：用户可以在该系统中查询已下的订单、收藏的商品、我的优惠券、团购等信息。
  - 订单系统：提供下单、查询订单、修改订单状态、定时处理订单。
  - 搜索系统：提供商品的搜索功能。
  - 单点登录系统：为多个系统之间提供用户登录凭证以及查询登录用户的信息。

- 服务层
  - shopping-sso 单点登录系统
  - shopping-rest 商品服务层，提供信息，内容服务。
  - shopping-order 订单服务层,提供订单基础服务。
  - shopping-search 商品搜索服务，基于solr索引库。
  - shopping-manage 后台服务层，提供后台基础服务。
  - shopping-content CMS服务层，提供内容管理。
  - shopping-sso 单点登录服务层，提供sso基础服务。
  - shopping-sso-web 单点登录表现层，调用了sso的服务。
  - shopping-cart 购物车服务层，提供了购物车的基础服务。
  - shopping-order 订单服务层，提供了订单基础服务。
 
- 表现层
  - shopping-manage-web 后台表现层，供管理员完成对网站商品和内容的增删改查,调用了manage、content、search的服务。
  - shopping-order-web 订单表现层，调用了order、cart、sso的服务
  - shopping-cart-web 购物车表现层，调用了cart、manage、sso的服务。
  - shopping-search-web 搜索表现层，调用了search的服务。
  - shopping-item-web 商品详情表现层，调用了manage的服务。
  - shopping-portal-web 前台表现层，作为网站的门户，与用户交互,调用了content的服务。
  - shopping-order-web  订单表现层，调用了order、cart、sso的服务
  
- 数据库
    - mysql作为关系型数据库，提供了数据的持久化。
    - redis作为内存数据库，提供高性能的缓存服务。
    
 # 使用的中间件
 
## Nginx
Nginx是一款高性能的http服务器/反向代理服务器及电子邮件（IMAP/POP3）代理服务器。 

## Redis
Redis是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。 

## 搜索应用服务器Solr
Solr是一个独立的企业级搜索应用服务器，它对外提供类似于Web-service的API接口。用户可以通过http请求，向搜索引擎服务器提交一定格式的XML文件，生成索引；也可以通过Http Get操作提出查找请求，并得到XML格式的返回结果。

## 消息服务Activemq
ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。
