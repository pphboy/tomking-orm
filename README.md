# Tomking

Tomking将成为一个类似于集成SSM的框架，可以在一个项目内，完成项目搭建。这虽然不是解耦，但可以加快个人开发者的开发速度。配置文件统一，简单

 Tomking-ORM 框架测试代码 网址：[tomking-orm-test-example](https://gitee.com/pphboy/tomking-orm-test-example)

### 功能表-完成进度

- [x] Tomking-ORM
	* 这里的ORM是无XML化的
   		 * 但不知如何完成动态SQL查询，也许这也可以用注解实现
       * 两个注解，Select 和 Modify
       * 查询速度：7ms左右
       * 使用 Druid作为框架底层线程池和连接池的支持
       * 原汁原味的SQL使用
- [ ] 使用文档的编写
    * 会直接支持SpringBoot，因为人生苦短，多花点时间搞核心
    * 后期会抽时间进行编写
- [ ] 对象操作
    * 我不确定我是否有足够的时间进行开发JPA这种功能，但我会尽全力将这个技术维护好
    * 可能并不支持Spring，因为我打算自己搞一个容器框架，用来做网络数据共享的
        