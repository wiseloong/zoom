# zoom-security
安全框架-同时也是一个依赖组件

设想，最终包含api和web2个方面的安全管理，先实现api的。

支持3种认证方式：token，session，session-token

自动判断不同环境使用不同认证管理，有redis时默认使用token模式，没有redis时默认使用session模式，同时也支持指定方式。

## 初始化

有一套默认的库表逻辑，使用配置`zoom.security.db-init=true`来开启，参考：[init.md](./init.md)

同时也支持自定义相应bean，来实现自己的逻辑。