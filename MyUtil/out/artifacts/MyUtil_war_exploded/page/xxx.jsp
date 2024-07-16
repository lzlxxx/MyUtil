<%--
  Created by IntelliJ IDEA.
  User: nan
  Date: 2024/6/30
  Time: 下午8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>分页示例</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }
        .page {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .pagination {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 5px;
            flex-wrap: wrap;
        }
        .pagination a,
        .pagination button {
            display: inline-block;
            padding: 10px 15px;
            border-radius: 4px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #333;
            background-color: #f8f9fa;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s, border-color 0.3s;
        }
        .pagination a.active,
        .pagination button.active {
            background-color: #007bff;
            color: #fff;
            border-color: #007bff;
        }
        .pagination a.disabled,
        .pagination button.disabled {
            pointer-events: none;
            background-color: #ddd;
            color: #aaa;
            border-color: #ddd;
        }
        .pagination a:hover,
        .pagination button:hover {
            background-color: #e9ecef;
            border-color: #ccc;
        }
        .pagination input {
            width: 50px;
            padding: 5px;
            margin: 0 5px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }
        .pagination button.goto {
            background-color: #007bff;
            color: white;
            border: none;
        }
        .pagination button.goto:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="page">
    <div class="pagination">
        <a href="${page.path}&pageNumber=1" class="${page.pageNumber <= 1 ? 'disabled' : ''}">首页</a>
        <a href="${page.path}&pageNumber=${page.pageNumber-1}" class="${page.pageNumber <= 1 ? 'disabled' : ''}">上一页</a>
        第${page.pageNumber}页
        <a href="${page.path}&pageNumber=${page.pageNumber + 1}" class="${page.pageNumber >= page.totalPage ? 'disabled' : ''}">下一页</a>
        共${page.totalPage}页 / 共${page.totalRecord}个
        转到第
        <input id="setPage" type="number" value="${page.pageNumber}" min="1" max="${page.totalPage}"/>页，
        <button id="goto" class="goto">跳转</button>
        <a href="${page.path}&pageNumber=${page.totalPage}" class="${page.pageNumber >= page.totalPage ? 'disabled' : ''}">末页</a>
    </div>
</div>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
        var gotoBtn = document.getElementById("goto");
        gotoBtn.addEventListener("click", function () {
            var setPage = document.getElementById("setPage").value;
            var totalPage = parseInt(document.getElementById("setPage").max);
            if (setPage < 1 || setPage > totalPage) {
                alert("请输入有效的页码！");
                return;
            }
            window.location.href = "${page.path}&pageNumber=" + setPage;
        });
    });
</script>
</body>
</html>
