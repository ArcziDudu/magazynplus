#FROM node:18.13.0-alpine
#RUN npm install -g @angular/cli
#WORKDIR /frontend
#COPY package.json .
#RUN npm install --force
#COPY . .
#EXPOSE 4200
#CMD ng serve --host 0.0.0.0
#

# Etap 1: Instalacja Angular CLI
FROM node:18.13.0-alpine as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install --force
RUN npm run build
# Etap 3: Konfiguracja Nginx
FROM nginx:latest
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/angular-app /usr/share/nginx/html
# Expose port 80
EXPOSE 4200
