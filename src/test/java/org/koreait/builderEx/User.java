package org.koreait.builderEx;

public class User {

    private String email;
    private String password;
    private String name;

    private User() {}

    public static Builder builder() {
        return new Builder();
    }

    // 정적 내부 클래스
    static class Builder { // User 클래스 객체를 생성하고 조립
        private User instance = new User();
        public Builder email(String email) {
            // 메서드 체이닝 방식
            instance.email = email;

            return this;
        }

        public Builder password(String password) {
            // 메서드 체이닝 방식
            instance.password = password;

            return this;
        }

        public Builder name(String name) {
            // 메서드 체이닝 방식
            instance.name = name;

            return this;
        }

        public User build() {
            return instance;
        }
    }
}
