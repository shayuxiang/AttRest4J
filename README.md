

## AttRest4J

 - 简介

>  **AttRest4J是AttRest的Java版本，依赖于springboot应用，使用它可以轻松地构建一个前后台分离的本地引用。从而实现完美的前后台分离方案。客户端包括Jquery、Vue.js、React、Angular等**
> AttRest(Java) is a pulgin for springboot application, It can build a local library for Vue.js/Jquery/Angular/React,which is the local  API's reference

 - 背景

> **在传统的前后台分离的开发中，通常的流程是由前端实现展示和效果，后台实现业务与计算，两者互不干扰。然而，当两者进行对接时，却时常会产生一些不可预知的问题或错误，从而导致部分重构，从而导致降低项目效率，增加开发人员的工作量。AttRest正是为解决这一问题而生。**
> In the traditional development of separation of foreground and background, the common process is to realize the display and effect by the front end, and realize the business and calculation in the background. However, when the two are connected, they often produce some unpredictable problems or errors, which leads to partial refactoring, which reduces the project efficiency and increases the workload of developers. Attrest was born to solve this problem。

1、**示例**

例如，如下接口代码：

```java
@RestController
@RequestMapping("/student")
public class StudentController{
	
	@Autowired
	private const StudentServices studentService;

	@RequestMapping(method="get",value="/getname/{id}")
	public String getName(long id){
			return studentServices.getName(id);
	}
}
```
接口非常的简单，目的也很明确，即通过输入学生的Id查询到学生的姓名。
接着，在传统的客户端，我们会通过ajax或Promise等框架去实现请求和响应，如下：

```javascript
function getName(id){
	axios.get("http://www.xxxx.com/student/getname",{id:id},function(result){
		console.log(result.name);
	});
}
```
这里会有一些问题，比如：

 1. 前端人员必须了解后台接口的调用路径，参数，Http请求的方法;或是后台人员至少要参与一些接口联调的前端开发任务，从而增加本身除实际业务之外的开发成本。
 2. 修改效率低，因为即使是接口内参数的变动，也有可能造成前端的需要重新编码、测试、发布。所以，即便是前后台分离，耦合度依然不低。
 3. 迁移、部署，牵一发而动全身。如果是开发初期使用的单个web服务实例，而最后再切入不同微服务，那么前端也必须跟着改动。
 4. 在接口改动的过程中，客户端并不会主动报错。浏览器并不知道您所输入的URL字符串是对还是错。这也就就会造成一些隐藏bug的存在。

所以，我们有一些新的方式，如下(以React为例)：

```javascript
function getName(id){
	React.$api.Student.getName(id,function(result){
			console.log(result.name)
	});
}
```
如您所见，在加入AttRest之后：

 1. 无论URL、Http Method 的变化，对前端始终屏蔽。前端只管实现界面和其基本业务，从而做到真正的解耦。
 2. 实现完全的面向对象，接口即对象，请求即方法。可读性更高。
 3. 方便统一分配管理，对于JS的代码，可操作性更高，支持一些前置后置AOP的操作。
 4. 一套代码，多套客户端复用。即使用AttRest，可以同时提供给多套不同的客户端操作，省去因环境不同而复制粘贴修改的麻烦。

 
 2、**接口优先模式**
AttRest内置了三种开发模式，以适用不同的开发场景。

>  数据服务优先(backendfirst)

这也是AttRest默认的模式，即所有API调用的准则，以后台的返回为基准，生成前端的调用。

>  界面实现优先(frontfirst)

>  双向弥补模式(duplex)


三种模式的比较
|模式|服务优先(backendfirst)|界面优先(frontfirst)|互补模式(duplex)|
|--|--|--|--|
|Schema-View|√|×|√|
|Local-Api|×|√|√|


 
 3、**服务端接入**

  >Maven：

 6. **客户端接入**

> npm:

 8. **配置**