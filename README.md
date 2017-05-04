# ModularizationArchitecture
基于SpinyTech的[ModularizationArchitecture](https://github.com/SpinyTech/ModularizationArchitecture)版本修改：
1. 简化修改为单进程版本
2. 重新设计了Request&Response的数据结构，使用Bundle当作参数载体
3. 修改同步&异步的结果处理，统一使用回调方式，回调函数的执行线程与调用时的线程相同
3. 实现Action懒加载，省点内存

TODO:
1. request的rxjava的支持
2. Provider Action的注解简化