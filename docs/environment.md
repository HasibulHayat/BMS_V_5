# Environment Configuration

## Database

```id="nsr3rp"
spring.datasource.url=jdbc:postgresql://localhost:5432/bms
spring.datasource.username=your_user
spring.datasource.password=your_password
```

---

## JWT

```id="0ix5e2"
jwt.secret=your-secret-key
jwt.expiration=86400000
```

---

## Notes

* Keep JWT secret secure
* Do not commit credentials to repository
