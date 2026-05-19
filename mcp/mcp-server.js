const { Server } = require("@modelcontextprotocol/sdk/server/index.js");
const {
  StdioServerTransport,
} = require("@modelcontextprotocol/sdk/server/stdio.js");
const {
  CallToolRequestSchema,
  ListToolsRequestSchema,
  TextContent,
} = require("@modelcontextprotocol/sdk/types.js");
const pg = require("pg");

const client = new pg.Client({
  host: process.env.DB_HOST || "db",
  port: process.env.DB_PORT || 5432,
  database: process.env.DB_NAME || "app",
  user: process.env.DB_USER || "postgres",
  password: process.env.DB_PASSWORD || "postgres",
});

const server = new Server(
  {
    name: "steam-like-school-project-db",
    version: "1.0.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

server.setRequestHandler(ListToolsRequestSchema, async () => {
  return {
    tools: [
      {
        name: "query_database",
        description: "Execute SQL queries (SELECT, INSERT, UPDATE, DELETE) on the Steam Like School Project database",
        inputSchema: {
          type: "object",
          properties: {
            query: {
              type: "string",
              description: "SQL query to execute (SELECT, INSERT, UPDATE, DELETE)",
            },
          },
          required: ["query"],
        },
      },
      {
        name: "list_tables",
        description: "List all tables in the database",
        inputSchema: {
          type: "object",
          properties: {},
          required: [],
        },
      },
      {
        name: "get_table_schema",
        description: "Get the schema (columns) of a specific table",
        inputSchema: {
          type: "object",
          properties: {
            table_name: {
              type: "string",
              description: "Name of the table",
            },
          },
          required: ["table_name"],
        },
      },
      {
        name: "execute_write",
        description: "Execute write operations (INSERT, UPDATE, DELETE) and return affected row count",
        inputSchema: {
          type: "object",
          properties: {
            query: {
              type: "string",
              description: "SQL write query to execute (INSERT, UPDATE, DELETE). Use RETURNING clause to see returned data.",
            },
          },
          required: ["query"],
        },
      },
    ],
  };
});

server.setRequestHandler(CallToolRequestSchema, async (request) => {
  try {
    if (request.params.name === "query_database") {
      const query = request.params.arguments.query;
      const result = await client.query(query);

      // For non-SELECT queries, include rowCount
      const response = {
        rows: result.rows,
        rowCount: result.rowCount,
        command: result.command,
      };

      return {
        content: [
          {
            type: "text",
            text: JSON.stringify(response, null, 2),
          },
        ],
      };
    } else if (request.params.name === "list_tables") {
      const result = await client.query(`
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public'
      `);
      return {
        content: [
          {
            type: "text",
            text: JSON.stringify(
              result.rows.map((r) => r.table_name),
              null,
              2
            ),
          },
        ],
      };
    } else if (request.params.name === "get_table_schema") {
      const tableName = request.params.arguments.table_name;
      const result = await client.query(`
        SELECT column_name, data_type, is_nullable
        FROM information_schema.columns
        WHERE table_name = $1
      `, [tableName]);
      return {
        content: [
          {
            type: "text",
            text: JSON.stringify(result.rows, null, 2),
          },
        ],
      };
    } else if (request.params.name === "execute_write") {
      const query = request.params.arguments.query;
      const result = await client.query(query);
      return {
        content: [
          {
            type: "text",
            text: JSON.stringify({
              success: true,
              rowCount: result.rowCount,
              command: result.command,
              rows: result.rows,
              message: `${result.command} query executed. Rows affected: ${result.rowCount}`,
            }, null, 2),
          },
        ],
      };
    }
    return {
      content: [
        {
          type: "text",
          text: "Unknown tool",
        },
      ],
    };
  } catch (error) {
    return {
      content: [
        {
          type: "text",
          text: `Error: ${error.message}`,
        },
      ],
      isError: true,
    };
  }
});

async function main() {
  try {
    await client.connect();
    console.error("Connected to database");

    const transport = new StdioServerTransport();
    await server.connect(transport);
    console.error("MCP Server running");
  } catch (error) {
    console.error("Fatal error:", error);
    process.exit(1);
  }
}

main();
