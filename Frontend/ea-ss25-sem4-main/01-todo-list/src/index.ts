import createTodoItem from './createTodoItem';

const form = document.getElementById('form') as HTMLFormElement;
const list = document.getElementById('list');

form.addEventListener('submit', (event) => {
  event.preventDefault();
  const data = new FormData(form);
  const todo = data.get('todo') as string;

  const li = createTodoItem(todo, () => {
    list.removeChild(li);
    form.todo.focus();
  });
  list.prepend(li);

  form.reset();
});
