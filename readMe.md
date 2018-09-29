gitHub地址：https://github.com/ypcouc/common_tool <br>
##自动生成doclever接口文档<br>
1、doclever部署请参考：https://github.com/sx1989827/DOClever；<br>
2、根据类属性，自动生成接口的入参及出参，<br>
注意属性注释要以"/** 字段说明 */"格式才能识别自动填入接口字段备注；<br>
3、可单独生成一个get或post接口，也可一次生成增、删、改、查接口；<br>
4、实现类com.ypc.doclever.GenerateDocLeverAPI，调用例子在其中的main方法里面。<br>
<br>
##Code generator代码生成器<br>
1、根据mysql中的表自动生成相应实体类（包含字段说明），以及自动生成增、删、改、查功能；<br>
从controller>service>dao>mapper.xml一次全部生成，减少我们开发工作量。<br>
2、根据elasticsearch数据结构实体类自动生成对其index下的document的增、删、改、查功能，<br>
从controller>service>dao，使用的是ElasticsearchTemplate+ElasticsearchRepository操作ES。<br>
<br>
##常用工具类<br>
1、时间处理工具 DateUtils<br>
2、字符处理工具 CharUtils<br>
3、实体属性拷贝工具 BeanToolUtils<br>
4、文件处理工具 FileUtils<br>
5、excle文件读取导出工具 com.ypc.excel包下<br>
6、图片处理工具 ImageOperateUtils<br>
7、发送邮件工具 SendEmailUtil<br>
8、反射工具 ReflectionUtils<br>
<br>
持续更新中......<br>
联系方式：qq：714782688<br>
欢迎加qq一起讨论

