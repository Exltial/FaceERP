# FaceERP
基于人脸识别的ERP后端
* 1：在虹软官网注册账号，下载sdk：https://ai.arcsoft.com.cn/ucenter/resource/build/index.html
* 2：SDK版本：人脸识别ArcFace(v2.2) java语言版本
* 3：拷贝sdk里的三个dll文件到bootstrap/resources/lib
* 4：复制appid和key替换application配置文件里的值
* 5：安装sdk里的jar包到本地maven仓库，命令：mvn install:install-file -Dfile=jar包的位置 -DgroupId=com.arcsoft.face -DartifactId=arcsoft-sdk-face -Dversion=2.2.0.1 -Dpackaging=jar
