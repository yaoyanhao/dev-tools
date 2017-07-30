<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>论文统计</title>
</head>
<body>
<h1>论文统计</h1>
<form action="<%=request.getContextPath() %>/doc/query">

  <table>
    <tr>
      <td>关键词：</td>
      <td>
        <input name="keyWord" type="text"/>
      </td>
    </tr>
    <tr>
      <td>
      <td colspan="2"><input type="submit" value="提交"/></td>
      </td>
    </tr>

  </table>

</form>
</body>
</html>