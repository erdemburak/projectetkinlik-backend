## TechCareer Project Etkinlik

### Burak Erdem
### Mustafa Sarıtaş

Etkinlik projesinin back-end bölümü bu repository içerisinde bulunmaktadır. Rest-Api kullanılarak hazırlanan servisler, etkinlikleri listeleme, kaydetme ve silme işlemleri uygulamaktadır.

Bu projenin docker imajı oluşturuldu ve Aws üzerinden deploy edildi.

- DockerHub : https://hub.docker.com/r/erdemburak/projectetkinlikv2
- Tüm Etkinlikleri listelemek için: http://51.20.142.51/api/v1/etkinlik/list
- Etkinlik oluşturmak için : http://51.20.142.51/api/v1/etkinlik/create
- Id'ye göre etkinlik çekmek için: http://51.20.142.51/api/v1/etkinlik/list/{id}
- Id'ye göre etkinlik silmek için: http://51.20.142.51/api/v1/etkinlik/delete/{id}
