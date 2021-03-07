平时可能会有要地的项目需要上传到github进行管理，以下记录通过IDEA如何将项目提交到github上。

1. 通过IDEA创建一个本地git仓库，步骤：VCS -> Import into Version Control -> Create Git Repository...,这时目录中的文件名会变成红色，表示文件未提交到本地git仓库；
2. 右键工程文件名 -> git -> add，这时文件名会变成绿色；
3. 右键工程文件名 -> git -> commit，提交到本地git仓库，通过这3个步骤已经代码提交到本地git仓库；
4. 需要到github上新建一个新的repository工程，用于将本地项目上传到该工程下；
5. 拷贝远程项目地址：https://github.com/lwnull2018/sb_multi_module_base.git
6. VCS -> Git -> Remotes...，将远程项目地址添加上去；
7. VCS -> Git -> Push...，将代码提交到远程服务器；

