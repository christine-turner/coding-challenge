####
# Development Dockerfile for Angular Movie UI
# Uses ng serve for hot-reload and fast development
####

FROM node:20-alpine

WORKDIR /app

# Copy package files for better caching
COPY package.json yarn.lock ./

# Install dependencies using yarn
# --frozen-lockfile ensures exact versions from yarn.lock
RUN yarn install --frozen-lockfile

# Copy source code
COPY . .

# Expose Angular dev server port
EXPOSE 4200

# Start development server
# --host 0.0.0.0 allows external connections
# --poll 2000 enables file watching in Docker
CMD ["yarn", "start", "--", "--host", "0.0.0.0", "--poll", "2000"]