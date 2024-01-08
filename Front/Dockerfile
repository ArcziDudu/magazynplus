FROM node:18.13.0-alpine as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install --force
RUN npm run build

FROM nginx:latest
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/angular-app /usr/share/nginx/html

EXPOSE 4200
