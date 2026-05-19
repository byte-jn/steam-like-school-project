FROM node:20-alpine

WORKDIR /app

COPY package.json ./
RUN npm install --production

COPY mcp-server.js ./

USER nobody

ENTRYPOINT ["node", "mcp-server.js"]
