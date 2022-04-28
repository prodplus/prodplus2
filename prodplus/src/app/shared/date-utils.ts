export function toDateApi(d: Date): string {
  let m = '' + (d.getUTCMonth() + 1);
  let y = '' + d.getUTCFullYear();
  let x = '' + d.getUTCDate();

  if (m.length < 2) m = '0' + m;
  if (x.length < 2) x = '0' + x;

  return [y, m, x].join('-');
}

export function toDateTimeApi(d: Date): string {
  let m = '' + (d.getUTCMonth() + 1);
  let y = '' + d.getUTCFullYear();
  let x = '' + d.getUTCDate();
  let h = '' + d.getUTCHours();
  let i = '' + d.getUTCMinutes();

  if (m.length < 2) m = '0' + m;
  if (x.length < 2) x = '0' + x;
  if (h.length < 2) h = '0' + h;
  if (i.length < 2) i = '0' + i;

  return [y, m, x].join('-') + 'T' + [h, i].join(':');
}

export function toTimeString(d: Date): string {
  let h = '' + d.getUTCHours();
  let m = '' + d.getUTCMinutes();
  let s = '' + d.getUTCSeconds();

  if (h.length < 2) h = '0' + h;
  if (m.length < 2) m = '0' + m;
  if (s.length < 2) s = '0' + s;

  return [h, m, s].join(':');
}
