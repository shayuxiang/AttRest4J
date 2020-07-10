

## AttRest4J

 - ���

>  **AttRest4J��AttRest��Java�汾��������springbootӦ�ã�ʹ�����������ɵع���һ��ǰ��̨����ı������á��Ӷ�ʵ��������ǰ��̨���뷽�����ͻ��˰���Jquery��Vue.js��React��Angular��**
> AttRest(Java) is a pulgin for springboot application, It can build a local library for Vue.js/Jquery/Angular/React,which is the local  API's reference

 - ����

> **�ڴ�ͳ��ǰ��̨����Ŀ����У�ͨ������������ǰ��ʵ��չʾ��Ч������̨ʵ��ҵ������㣬���߻������š�Ȼ���������߽��жԽ�ʱ��ȴʱ�������һЩ����Ԥ֪���������󣬴Ӷ����²����ع����Ӷ����½�����ĿЧ�ʣ����ӿ�����Ա�Ĺ�������AttRest����Ϊ�����һ���������**
> In the traditional development of separation of foreground and background, the common process is to realize the display and effect by the front end, and realize the business and calculation in the background. However, when the two are connected, they often produce some unpredictable problems or errors, which leads to partial refactoring, which reduces the project efficiency and increases the workload of developers. Attrest was born to solve this problem��

1��**ʾ��**

���磬���½ӿڴ��룺

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
�ӿڷǳ��ļ򵥣�Ŀ��Ҳ����ȷ����ͨ������ѧ����Id��ѯ��ѧ����������
���ţ��ڴ�ͳ�Ŀͻ��ˣ����ǻ�ͨ��ajax��Promise�ȿ��ȥʵ���������Ӧ�����£�

```javascript
function getName(id){
	axios.get("http://www.xxxx.com/student/getname",{id:id},function(result){
		console.log(result.name);
	});
}
```
�������һЩ���⣬���磺

 1. ǰ����Ա�����˽��̨�ӿڵĵ���·����������Http����ķ���;���Ǻ�̨��Ա����Ҫ����һЩ�ӿ�������ǰ�˿������񣬴Ӷ����ӱ����ʵ��ҵ��֮��Ŀ����ɱ���
 2. �޸�Ч�ʵͣ���Ϊ��ʹ�ǽӿ��ڲ����ı䶯��Ҳ�п������ǰ�˵���Ҫ���±��롢���ԡ����������ԣ�������ǰ��̨���룬��϶���Ȼ���͡�
 3. Ǩ�ơ�����ǣһ������ȫ������ǿ�������ʹ�õĵ���web����ʵ��������������벻ͬ΢������ôǰ��Ҳ������ŸĶ���
 4. �ڽӿڸĶ��Ĺ����У��ͻ��˲����������������������֪�����������URL�ַ����ǶԻ��Ǵ���Ҳ�;ͻ����һЩ����bug�Ĵ��ڡ�

���ԣ�������һЩ�µķ�ʽ������(��ReactΪ��)��

```javascript
function getName(id){
	React.$api.Student.getName(id,function(result){
			console.log(result.name)
	});
}
```
�����������ڼ���AttRest֮��

 1. ����URL��Http Method �ı仯����ǰ��ʼ�����Ρ�ǰ��ֻ��ʵ�ֽ���������ҵ�񣬴Ӷ����������Ľ��
 2. ʵ����ȫ��������󣬽ӿڼ��������󼴷������ɶ��Ը��ߡ�
 3. ����ͳһ�����������JS�Ĵ��룬�ɲ����Ը��ߣ�֧��һЩǰ�ú���AOP�Ĳ�����
 4. һ�״��룬���׿ͻ��˸��á���ʹ��AttRest������ͬʱ�ṩ�����ײ�ͬ�Ŀͻ��˲�����ʡȥ�򻷾���ͬ������ճ���޸ĵ��鷳��

 
 2��**�ӿ�����ģʽ**
AttRest���������ֿ���ģʽ�������ò�ͬ�Ŀ���������

>  ���ݷ�������(backendfirst)

��Ҳ��AttRestĬ�ϵ�ģʽ��������API���õ�׼���Ժ�̨�ķ���Ϊ��׼������ǰ�˵ĵ��á�

>  ����ʵ������(frontfirst)

>  ˫���ֲ�ģʽ(duplex)


����ģʽ�ıȽ�
|ģʽ|��������(backendfirst)|��������(frontfirst)|����ģʽ(duplex)|
|--|--|--|--|
|Schema-View|��|��|��|
|Local-Api|��|��|��|


 
 3��**����˽���**

  >Maven��

 6. **�ͻ��˽���**

> npm:

 8. **����**