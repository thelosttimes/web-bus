<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/2/27
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <title>Title</title>
    <script type="text/javascript">
        function save(pick) {
            var $pick = $(pick);
            var val = $pick.attr('checked');
            var id = $pick.val();
            if (val == null) {
                console.log("启用");
                if (id == null || id == '') {
                    alert("请检查插件id！")
                    return false;
                }
                $.get('active.do', {"id": id}, function (data) {
                    alert(data);
                }, "text");
                $pick.attr("checked", 'checked');
            }
            else {
                console.log("禁用");
                if (id == null || id == '') {
                    alert("请检查插件id！")
                    return false;
                }
                $.get('disable.do', {"id": id}, function (data) {
                    alert(data);
                }, "text");
                $pick.removeAttr("checked")

            }
        }
    </script>
</head>
<body>
<table border="2">
    <thead>
    <tr>
        <th>插件名称</th>
        <th>版本</th>
        <th>启用</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${havePlugins}" var="plugin">
        <tr>
            <td>${plugin.name}</td>
            <td>${plugin.version}</td>
            <td><input type="checkbox" value="${plugin.id}"
                       onclick="save(this)"
                       name="pick" ${plugin.active eq true ? "checked = 'checked'" : ""}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
