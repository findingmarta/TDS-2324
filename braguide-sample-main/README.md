# braguide-sample

Sample Android app with implementation of MVVM architecture with local and and remote data-access, using Room database and Retrofit

Basic rules:

- One repository per entity;
- Data-access logic in repositories. "A repository can resolve conflicts between data sources (such as persistent models, web services, and caches) and centralize changes to this data"
- Data management logic separated from UI logic;
- View n - 1 ViewModel n - 1 Model;
- Singleton Database;
- Avoid heavy computation on main thread;
