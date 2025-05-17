<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Database Connection Test</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #1a73e8;
            text-align: center;
        }
        p {
            margin: 20px 0;
            line-height: 1.6;
        }
        .btn {
            display: inline-block;
            background-color: #1a73e8;
            color: #fff;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            margin: 20px 0;
        }
        .btn:hover {
            background-color: #0d62d0;
        }
        .center {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Database Connection Test</h1>
        <p>
            This page will help you test the connection to your database. Click the button below to run the test.
            The test will:
        </p>
        <ul>
            <li>Attempt to connect to the database</li>
            <li>Display database metadata if connection is successful</li>
            <li>List all tables in the database</li>
            <li>Run a simple test query</li>
        </ul>
        <div class="center">
            <a href="test-db" class="btn">Run Database Test</a>
        </div>
        <p>
            <strong>Note:</strong> If the test fails, check your database connection parameters in the DBContext class.
            Make sure the database server is running and accessible.
        </p>
    </div>
</body>
</html>
