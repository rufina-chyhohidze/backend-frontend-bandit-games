export const getPageContext = (): string => {
    const contentClone = document.body.cloneNode(true) as HTMLElement;

    const noiseSelectors = [
        'nav', 'footer', 'button', 'script', 'style',
        '[role="alert"]', '.sidebar', 'svg',
        '#ai-widget', '.ai-widget-container', '.ai-window'
    ];

    contentClone.querySelectorAll(noiseSelectors.join(',')).forEach(el => el.remove());

    const mainContent = contentClone.querySelector('main') || contentClone;

    const cleanText = (mainContent.innerText || '')
        .replace(/[\n\r]+/g, ' ')
        .replace(/\s+/g, ' ')
        .trim()
        .substring(0, 1000);

    return `
        Page Title: ${document.title}
        URL: ${window.location.pathname}
        Main Content: ${cleanText || "No readable content found."}
    `;
};