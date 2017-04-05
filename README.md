# LazyLoadFragment

## 原理
整个原理是把真实需要加载的Fragment包装到一个空LazyFragment里，通过控制LazyFragment来实现对真实Fragment的加载。具体效果就是需要惰性加载的类不需要改继承类，基本不用做任何修改，用LazyFragment包装下就能用。

## 使用方法
如果需要加载Fragment为SampleFragment，然后Bundle参数为bundle。

Fragment fragment = LazyFragment.newInstance(new LazyFragmentLoader(SampleFragment.class, bundle));

然后把fragment当成SampleFragment处理即可。

## 详细介绍
详细可以参考

http://www.jianshu.com/p/5e72e46295b2
