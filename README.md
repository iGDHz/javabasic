# javabasic
java基础练习
# Log
| 日期        | 说明                                                                                                                                                            |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 2022/12/4 | Java18中对反射机制进行了修改，新机制可以直接调用特定对象的方法句柄，可以为Loom项目（Java轻量级线程项目）减少本地堆栈的使用，从结果上来说，对于staic修饰的方法、字段有速度上的提升，但是在对具体对象方法反射的调用上速度则反而出现了下降，并在最终综合的测试结果上来看呈现一个下降的状态（JEP416） |