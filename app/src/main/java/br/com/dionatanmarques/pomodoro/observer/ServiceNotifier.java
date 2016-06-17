package br.com.dionatanmarques.pomodoro.observer;

public interface ServiceNotifier {
    void add(ListenValue obj);

    void notifyValue(String value);
}
