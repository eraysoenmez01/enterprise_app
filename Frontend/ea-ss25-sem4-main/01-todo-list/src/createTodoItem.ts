export default function createTodoItem(todo: string, onComplete: () => void) {
  const li = document.createElement('li');
  li.innerHTML = `
    <label>
      <input type="checkbox" />
      <span>${todo}</span>
    </label>
  `;
  li.addEventListener('click', (event) => {
    const target = event.target as Element;
    const isInput = target.tagName === 'INPUT';
    if (!isInput) return;

    onComplete();
  });
  return li;
}
