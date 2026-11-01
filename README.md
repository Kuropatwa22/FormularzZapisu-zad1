# Zadanie 1 — Formularz zapisu

Zrobiłem formularz do zapisów na kurs na podstawie rozdziału 8.

Formularz ma ScrollView, żeby można było go przewijać na telefonie.

Użytkownik może wpisać imię i e-mail, wybrać datę urodzenia, kurs i tryb zajęć oraz ustawić poziom zaawansowania.

Dodałem też newsletter i akceptację regulaminu.

Po kliknięciu przycisku program sprawdza dane. Jeśli coś jest niepoprawne, pokazuje błąd.

Jeśli wszystko jest poprawne, pojawia się okno potwierdzenia, a później podsumowanie i Snackbar.

## Dlaczego przy SeekBar nie dało się użyć lambdy, a przy CheckBox tak?

Przy SeekBar nie można użyć lambdy, ponieważ jego listener ma trzy metody, które trzeba obsłużyć.

Przy CheckBox można użyć lambdy, ponieważ jego listener ma tylko jedną metodę.

Dlatego przy SeekBar użyłem klasy anonimowej, a przy CheckBox lambdy.
