const fs = require('fs');
const path = require('path');

const rootDir = path.resolve(__dirname, '..');
const distAppsDir = path.join(rootDir, 'dist', 'apps');
const prodDir = path.join(rootDir, 'dist', 'prod');

console.log('🚀 Starting Magic Mind Production Assembly...');

if (!fs.existsSync(distAppsDir)) {
  console.error('❌ dist/apps directory not found! Run "nx run-many -t build" first.');
  process.exit(1);
}

// 1. Clean or create dist/prod
if (fs.existsSync(prodDir)) {
  fs.rmSync(prodDir, { recursive: true, force: true });
}
fs.mkdirSync(prodDir, { recursive: true });

// Helper to copy directory contents
function copyDirectory(source, destination) {
  if (!fs.existsSync(source)) {
    console.error(`❌ Source directory does not exist: ${source}`);
    process.exit(1);
  }
  fs.mkdirSync(destination, { recursive: true });
  fs.cpSync(source, destination, { recursive: true });
}

// 2. Copy Shell to dist/prod
const shellBrowser = path.join(distAppsDir, 'shell', 'browser');
console.log(`📦 Copying Shell from ${shellBrowser} to ${prodDir}...`);
copyDirectory(shellBrowser, prodDir);

// 3. Copy Remotes into subfolders
const remotes = ['admin', 'teacher', 'student'];
for (const remote of remotes) {
  const remoteBrowser = path.join(distAppsDir, remote, 'browser');
  const remoteDest = path.join(prodDir, remote);
  console.log(`📦 Copying Remote [${remote}] from ${remoteBrowser} to ${remoteDest}...`);
  copyDirectory(remoteBrowser, remoteDest);
  
  // Delete the remote's index.html to ensure SPA fallback hits the Shell's index.html
  const remoteIndex = path.join(remoteDest, 'index.html');
  if (fs.existsSync(remoteIndex)) {
    fs.unlinkSync(remoteIndex);
    console.log(`🗑️ Deleted ${remote}/index.html to prevent routing conflicts.`);
  }
}

// 4. Generate Production federation.manifest.json with relative paths
const prodManifest = {
  admin: '/admin/remoteEntry.json',
  student: '/student/remoteEntry.json',
  teacher: '/teacher/remoteEntry.json'
};
const manifestContent = JSON.stringify(prodManifest, null, 2);
fs.writeFileSync(path.join(prodDir, 'federation.manifest.json'), manifestContent, 'utf-8');
for (const remote of remotes) {
  const remoteDest = path.join(prodDir, remote);
  if (fs.existsSync(remoteDest)) {
    fs.writeFileSync(path.join(remoteDest, 'federation.manifest.json'), manifestContent, 'utf-8');
  }
}
console.log('✅ Production federation.manifest.json generated at root and subfolders');

// 5. Inject API_BASE_URL into index.html if available in environment
const apiUrl = process.env.API_BASE_URL || process.env.BACKEND_URL || '';
const indexPath = path.join(prodDir, 'index.html');
if (fs.existsSync(indexPath)) {
  let indexContent = fs.readFileSync(indexPath, 'utf-8');
  if (apiUrl) {
    console.log(`🔗 Injecting API_BASE_URL: ${apiUrl}`);
    indexContent = indexContent.replace(
      /window\.__MAGIC_MIND_API_URL__\s*=\s*window\.__MAGIC_MIND_API_URL__\s*\|\|\s*'';/,
      `window.__MAGIC_MIND_API_URL__ = '${apiUrl.trim()}';`
    );
  }
  fs.writeFileSync(indexPath, indexContent, 'utf-8');
}

// 6. Copy vercel.json if present
const vercelJsonSrc = path.join(rootDir, 'vercel.json');
if (fs.existsSync(vercelJsonSrc)) {
  fs.copyFileSync(vercelJsonSrc, path.join(prodDir, 'vercel.json'));
}

// 7. Ensure es-module-shims.js is present for tablets and mobile browsers
const esmsSrc = path.join(rootDir, 'node_modules', 'es-module-shims', 'dist', 'es-module-shims.js');
const esmsDest = path.join(prodDir, 'es-module-shims.js');
if (!fs.existsSync(esmsDest) && fs.existsSync(esmsSrc)) {
  fs.copyFileSync(esmsSrc, esmsDest);
  console.log('✅ es-module-shims.js copied to dist/prod');
}

console.log('🎉 Magic Mind Production Bundle assembled successfully in dist/prod!');
