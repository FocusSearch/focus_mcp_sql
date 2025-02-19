# FOCUS DATA MCP Server [[English](./README.md)]

模型上下文协议（MCP）服务器使人工智能助手能够将自然语言转换为SQL语句。

## 特征

- 初始化模型
- 将自然语言转换为SQL语句

## 准备

- jdk 23 或者以上版本
- gradle 8.12 或者以上版本
- 注册 [Datafocus](https://www.datafocus.ai/) 账号获取 Bearer Token: 
    1. 进入 [Datafocus](https://www.datafocus.ai/) 官网注册账号
    2. 创建一个应用
    3. 进入应用
    4. 系统配置 -> 接口鉴权 -> 承载令牌 -> 新增承载令牌

## 安装

1. Clone this repository:

```bash
git clone https://github.com/FocusSearch/focus_mcp_sql.git
cd focus_mcp_sql
```

2. Build the server:

```bash
gradle clean
gradle bootJar

生成的 jar 包路径: build/libs/focus_mcp_sql.jar
```

## MCP 配置文件

添加一下配置到 MCP 的配置文件 (通常在以下目录 `~/AppData/Roaming/Code/User/globalStorage/saoudrizwan.claude-dev/settings/cline_mcp_settings.json`):

```json
{
  "mcpServers": {
    "focus_mcp_data": {
      "command": "java",
      "args": [
        "-jar",
        "path/to/focus_mcp_sql/focus_mcp_sql.jar"
      ],
      "autoApprove": [
        "gptText2sqlStart",
        "gptText2sqlChat"
      ]
    }
  }
}
```

## 可用的工具列表

### 1. gptText2sqlStart

初始化模型.

**参数:**

- `model` (必须): 注册的表模型
- `bearer` (必须): bearer token
- `language` (可选): 语言 ['english','chinese']

**样例:**

```json
{
  "model": {
    "tables": [
      {
        "columns": [
          {
            "columnDisplayName": "name",
            "dataType": "string",
            "aggregation": "",
            "columnName": "name"
          },
          {
            "columnDisplayName": "address",
            "dataType": "string",
            "aggregation": "",
            "columnName": "address"
          },
          {
            "columnDisplayName": "age",
            "dataType": "int",
            "aggregation": "SUM",
            "columnName": "age"
          },
          {
            "columnDisplayName": "date",
            "dataType": "timestamp",
            "aggregation": "",
            "columnName": "date"
          }
        ],
        "tableDisplayName": "test",
        "tableName": "test"
      }
    ],
    "relations": [

    ],
    "type": "mysql",
    "version": "8.0"
  },
  "bearer": "ZTllYzAzZjM2YzA3NDA0ZGE3ZjguNDJhNDjNGU4NzkyYjY1OTY0YzUxYWU5NmU="
}
```

model 参数说明：

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
| model|body|object| 是 |none|
|» type|body|string| 是 |数据库类型|
|» version|body|string| 是 |数据库版本|
|» tables|body|[object]| 是 |表结构列表|
|»» tableDisplayName|body|string| 否 |表显示名|
|»» tableName|body|string| 否 |表原始名|
|»» columns|body|[object]| 否 |表列列表|
|»»» columnDisplayName|body|string| 是 |列显示名|
|»»» columnName|body|string| 是 |列原始名|
|»»» dataType|body|string| 是 |列数据类型|
|»»» aggregation|body|string| 是 |列聚合方式|
|» relations|body|[object]| 是 |表关联关系列表|
|»» conditions|body|[object]| 否 |关联条件|
|»»» dstColName|body|string| 否 |dimension 表关联列原始名|
|»»» srcColName|body|string| 否 |fact 表关联列原始名|
|»» dimensionTable|body|string| 否 |dimension 表原始名|
|»» factTable|body|string| 否 |fact 表原始名|
|»» joinType|body|string| 否 |关联类型|

### 2. gptText2sqlChat

将自然语言转换成SQL.

**参数:**

- `chatId` (必须): 初始化对话的对话id
- `input` (必须): 需要转换的自然语言
- `bearer` (必须): bearer token

**样例:**

```json
{
  "chatId": "03975af5de4b4562938a985403f206d4",
  "input": "max(age)",
  "bearer": "ZTllYzAzZjM2YzA3NDA0ZGE3ZjguNDJhNDjNGU4NzkyYjY1OTY0YzUxYWU5NmU="
}
```

## 响应结构

工具返回的数据结构如下:

### 成功响应

```json
{
  "errCode": 0,
  "exception": "",
  "msgParams": null,
  "promptMsg": null,
  "success": true,
  "data": {
  }
}
```

### 失败响应

```json
{
  "errCode": 1001,
  "exception": "",
  "msgParams": null,
  "promptMsg": null,
  "success": false,
  "data": null
}
```
