应用名称：赶集网招聘信息网络爬虫
主要工具类：ReportsUtil
        getHtmlUri()方法，传进url，返回html信息
        findMessage()方法，找出有用信息，返回List<HashMap<String,String>> 类型数据-->统计工作
        getJobInfo()方法，找出有用信息，返回List<HashMap<String,String>> 类型数据-->job招聘信息
注：WebRoot文件夹下js中有个重要的自定义表格控件，已经封装好了，在统计工作上有应用。也就是job信息，并未展示