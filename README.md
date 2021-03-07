##项目结构说明
该项目是实践了SpringBoot+Maven多模块间项目的依赖、打包成可执行jar包的过程，亲测可行。

项目结构，按技术工程角度进行的模块划分，结构如下 ：

    父工程father：
                    子模块 common  (共用的基础模块)
                    子模块 dao     (用于持久化数据跟数据库交互)
                    子模块 entity  (实体类、DTO类)
                    子模块 service (业务逻辑处理模块)
                    子模块 web     (实际的业务模块，页面交互接收，唯一有启动类的模块)
                    
    关系：
                    web依赖 common、dao、entity、service
                    service依赖 common、dao、entity
                    dao依赖 entity
                    entity独立的模块，谁也不依赖
                    common也是独立的模块，谁也不依赖
                    

几点说明：

    1. 该工程的目的是聚合多模块工程的一个简单例子，可直接打包成可运行的jar包；
    2. web模块是唯一带有启动类的模块，只有web模块和父模块的pom文件带有build，其他的子模块不需要build；
    3. 如果需要增加启动入口，就类似于新增个web项目；
    4. 关于application.properties配置文件，只需要在启动的模块中配置即可；
    5. 集成了mysql、mybatis、redis缓存、rocketmq消息列表、redission分布式锁；
    
    
    